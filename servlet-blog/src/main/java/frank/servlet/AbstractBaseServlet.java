package frank.servlet;

import frank.exception.BaseException;
import frank.model.Result;
import frank.uti.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractBaseServlet extends HttpServlet {

    private static final ConcurrentMap<String, Integer> MAP = new ConcurrentHashMap<>();

    private static final ConcurrentMap<String, AtomicInteger> MAP2 = new ConcurrentHashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        //前端约定好的统一返回的json数据格式
        Result result = new Result();
        try {
            //正确返回业务数据
            Object data = process(req, resp);
            result.setSuccess(true);
            result.setData(data);
        }catch (Exception e){//捕获到异常，需要设置前端需要错误信息，和堆栈信息
            if(e instanceof BaseException){
                BaseException be = (BaseException) e;
                result.setMessage("错误码："+be.getCode()+"，错误信息："+be.getMessage());
            }else{
                result.setMessage("服务器异常：未知的错误");
            }
            StringWriter sw = new StringWriter();
            PrintWriter epw = new PrintWriter(sw);
            e.printStackTrace(epw);
            result.setStackTrace(sw.toString());
        }
        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize(result));//返回json字符串
        pw.flush();

//        System.out.println("Scheme: "+req.getScheme());//协议号：http
//        System.out.println("ServletPath: "+req.getServletPath());//请求Servlet的路径：/articleList
//        System.out.println("ContextPath: "+req.getContextPath());//应用部署路径（上下文路径）：/blog
//        System.out.println("RequestURL: "+req.getRequestURL());//请求的全路径：http://localhost:8080/blog/articleList
//        System.out.println("RequestURI: "+req.getRequestURI());//ContextPath+ServletPath：/blog/articleList
//        System.out.println("PathInfo: "+req.getPathInfo());//null

        // 方法一：通过synchronized保证代码块的原子性
        synchronized (MAP) {//可以保证线程安全
            // 还会不会有线程安全问题？
            String path = req.getServletPath();
            Integer count = MAP.get(path);//线程安全的Map，api方法本身是线程安全
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            MAP.put(path, count);
        }

        // 方法二，通过AtomicInteger结合ConcurrentHashMap来保证线程安全
        String path = req.getServletPath();
        // ConcurrentHashMap.putIfAbsent()，如果没有键存在，就保存给定的键值对，返回null
        // 如果是键有映射关系，就直接返回对应的值
        AtomicInteger count = MAP2.putIfAbsent(path, new AtomicInteger(1));
        if(count != null)
            count.incrementAndGet();
    }

    public abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;

    public static ConcurrentMap<String, Integer> getMAP() {
        return MAP;
    }

    public static ConcurrentMap<String, AtomicInteger> getMAP2() {
        return MAP2;
    }
}
