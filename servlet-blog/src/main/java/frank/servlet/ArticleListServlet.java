package frank.servlet;

import frank.dao.ArticleDAO;
import frank.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/articleList")
public class ArticleListServlet extends AbstractBaseServlet {

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Article> articles = ArticleDAO.list();
        return articles;
//        return testData();
    }

    //测试数据
//    public static List<Article> testData(){
//        List<Article> articles = new ArrayList<>();
//        Article a1 = new Article();
//        a1.setId(1);
//        a1.setTitle("好啊u");
//        a1.setContent("寒江孤影，江湖故人");
//        a1.setUserId(1);
//        a1.setCreateTime(new Date());
//        Article a2 = new Article();
//        a2.setId(2);
//        a2.setTitle("随便写点啥");
//        a2.setContent("相逢何必曾相识");
//        a2.setUserId(1);
//        a2.setCreateTime(new Date());
//        articles.add(a1);
//        articles.add(a2);
//        return articles;
//    }
}
