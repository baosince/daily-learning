package reflection;

/**
 * @author yidxue
 */
public class Service1 {

    public String col1 = "field1";
    public static String col2 = "field2";

    public void doService1() {
        System.out.println("无参方法！");
    }

    public void doService1(int i) {
        System.out.println("有参方法 " + i);
    }

    public static void doService2() {
        System.out.println("static 无参方法!!");
    }

    public static void doService2(Integer i) {
        System.out.println("static 有参方法 " +i);
    }
}
