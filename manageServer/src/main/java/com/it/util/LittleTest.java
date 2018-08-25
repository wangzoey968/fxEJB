package com.it.util;

import com.it.api.table.user.Tb_Auth;
import com.it.api.table.user.Tb_Role;
import com.it.api.table.user.Tb_User;
import org.hibernate.Session;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzy on 2018/8/6.
 */
public class LittleTest {

    /**
     * 数据库返回数据的测试
     */
    @Test
    public void dataBaseTest() {
        Long uid = 1L;
        HibernateUtil.initSessionFactory();
        Session session = HibernateUtil.openSession();
        List list = session.createQuery("select user,auth from Tb_User user left join Tb_User_Auth  au on user.id=au.tb_user_id left join Tb_Auth  auth on au.tb_auth_id=auth.id where user.id=:id")
                .setParameter("id", uid).list();
        Tb_User u = null;
        List<Tb_Role> rs = session.createQuery("select role from Tb_User_Role  ur left join Tb_Role role on ur.tb_role_id=role.id where ur.tb_user_id=:uid").setParameter("uid", uid).list();
        for (Tb_Role role : rs) {
            List<Tb_Auth> as = session.createQuery("select auth from Tb_Auth auth left join Tb_Role_Auth ra on ra.tb_auth_id=auth.id where ra.tb_role_id=:rid").setParameter("rid", role.getId()).list();
            //角色下的权限
            role.setAuths(as);
        }
        for (Object o : list) {
            Object[] objects = (Object[]) o;
            Tb_User user = (Tb_User) objects[0];
            Tb_Auth auth = (Tb_Auth) objects[1];
            u = user;
            //扩展的权限

            u.getAuths().add(auth);
        }
        u.setRoles(rs);
        System.out.println(u.toString());
    }


    @Test
    public void ss() {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(String.format("%.2f%%",0.56f));

        this.getClass().getResourceAsStream("");//返回的是inputstream

        //ok
        URL url = this.getClass().getResource("");//url
        System.out.println(url);

        URL resource = Class.class.getResource("");//返回的是当前Class这个类所在包开始的为置
        System.out.println(resource);

        //ok
        URL res = Class.class.getResource("/");//返回的是classpath的位置
        System.out.println(res);

        //ok
        URL r = this.getClass().getClassLoader().getResource("");//返回的是classpath的位置
        System.out.println(r);

        URL u = this.getClass().getClassLoader().getResource("/");//错误的!!
        System.out.println(u);
    }

    @Test
    public void sss() throws Exception {
        /*SAXReader reader = new SAXReader();
        URI uri = FtpConfigService.class.getClass().getClassLoader().getResource("ConfigServer.xml").toURI();
        Document document = reader.read(Paths.get(uri).toFile());
        //获取根节点元素对象
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        for (Element s : list) {
            System.out.println(s.getName() + "/" + s.getText());
        }*/
        ArrayList<String> list_1 = new ArrayList<>();
        list_1.add("111");
        list_1.add("2222");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("1111111111");
        list2.add("22222222222");
        list_1.addAll(list2);
        for (String s : list_1) {
            System.out.println(s);
        }
    }

    @Test
    public void ssssss(){

    }

}
