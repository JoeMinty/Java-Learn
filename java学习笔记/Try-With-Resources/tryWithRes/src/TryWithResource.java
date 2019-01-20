public class TryWithResource {

    public static void main(String[] args) {
//        try{
//            test();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try(Connection conn =new Connection()) {
            conn.sendData();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    private static void test() throws Exception{
        Connection conn = null;
        try{
            conn = new Connection();
            conn.sendData();
        } finally{
            if(conn !=null) {
                conn.close();
            }
        }
    }
}

