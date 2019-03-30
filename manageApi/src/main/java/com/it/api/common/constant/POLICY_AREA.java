package com.it.api.common.constant;

import java.util.Arrays;
import java.util.List;

public class POLICY_AREA {

    public static final String SHANG_HAI = "上海";

    public static final String SU_ZHOU = "苏州";

    public static final String CHANG_ZHOU = "常州";

    public static final String KUN_SHAN = "昆山";

    public static final String JIA_XING = "嘉兴";

    public static List<String> getAllArea() {
        return Arrays.asList(SHANG_HAI, SU_ZHOU, CHANG_ZHOU, KUN_SHAN, JIA_XING);
    }

}
