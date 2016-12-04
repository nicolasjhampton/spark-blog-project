import com.teamtreehouse.blog.dao.BlogDao;
import com.teamtreehouse.blog.dao.NotFoundException;
import com.teamtreehouse.blog.dao.SimpleBlogDAO;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.Comment;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by nicolasjhampton on 11/5/16.
 */
public class Main {

    public static void main(String[] args) {
        staticFileLocation("/public");
        BlogDao dao = new SimpleBlogDAO();

        before((req, res) -> {
            //System.out.printf("%s%n", req.cookie("password"));
            if(req.cookie("password") != null) {
                req.attribute("password", req.cookie("password"));
            }
        });

        before("/entry", (req, res) -> {
            //System.out.printf(req.attribute("password"));
            //System.out.printf("%b", req.attribute("username") == "admin");
            if(req.attribute("password") == null || !req.attribute("password").equals("admin")) {
                //System.out.printf(req.attribute("password"));
                res.redirect("/login");
                halt();
            }
        });

        get("/", (req, res) -> {
            Map<String, List<BlogEntry>> model = new HashMap<>();
            model.put("entries", dao.findAllEntries());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/login", (req, res) -> new ModelAndView(null, "login.hbs"), new HandlebarsTemplateEngine());

        post("/login", (req, res) -> {
            //Map<String, String> model = new HashMap<>();
            String password = req.queryParams("password");
            res.cookie("password", password);
            res.redirect("/");
            return null;
        });

        get("/entry", (req, res) -> {
            return new ModelAndView(null, "new.hbs");
        }, new HandlebarsTemplateEngine());

        post("/entry", (req, res) -> {
            String title = req.queryParams("title");
            String entry = req.queryParams("entry");
            dao.addEntry(new BlogEntry(title, entry));
            res.redirect("/");
            return null;
        });

        get("/entry/:slug", (req, res) -> {
            Map<String, BlogEntry> model = new HashMap<>();
            String slug = req.params("slug");
            BlogEntry entry = dao.findEntryBySlug(slug);
            model.put("entry", entry);
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        post("/entry/:slug/comments", (req, res) -> {
            String slug = req.params("slug");
            BlogEntry entry = dao.findEntryBySlug(slug);

            String name = req.queryParams("name");
            String comment = req.queryParams("comment");

            entry.addComment(new Comment(name, comment));
            res.redirect("/entry/" + slug);

            return null;
        });

//        exception(NotFoundException.class, (exc, req, res) -> {
//            res.status(404);
//            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
//            String html = engine.render(
//                    new ModelAndView(null, "not-found.hbs"));
//            res.body(html);
//        });

    }
}
