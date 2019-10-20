# JavalinVC

### What is it?
JavalinVC is a View Controller Framework built upon Tipsy's [Javalin](https://github.com/tipsy/javalin/)






















# Starting Off
You will need to create a Javalin Object. You can read how to do that [here](https://javalin.io/documentation#getting-started). 

Then you will need to create a ResourceGrabber Again learn [here](https://github.com/wherkamp/JavalinVC/wiki/Resource-Grabbers). 

The website rules is simple. `WebsiteRulesBuilder.create().setName("Test - JavalinVC").setUrl("http://127.0.0.1:1234").build();`

And then last but not least. The View Manager read how to create that here [here](https://github.com/wherkamp/JavalinVC/wiki/ViewManager)

And once you have all of those you can create your JavalinVC.

```
JavalinVCBuilder.create().
//Set Javalin
setJavalin(javalin).
//Resource Grabber
setResourceGrabber(grabber).
// Set the rules
setRules(rules).
The View Manager and create
setViewManager(builder).createJavalinVC()
```

EXAMPLE TIME
```java
import io.javalin.Javalin;
import me.kingtux.javalinvc.JavalinVC;
import me.kingtux.javalinvc.JavalinVCBuilder;
import me.kingtux.javalinvc.WebsiteRules;
import me.kingtux.javalinvc.WebsiteRulesBuilder;
import me.kingtux.javalinvc.rg.ResourceGrabber;
import me.kingtux.javalinvc.rg.templategrabbers.InternalResourceGrabber;
import me.kingtux.javalinvc.view.ViewManager;
import me.kingtux.javalinvc.view.ViewManagerBuilder;
import org.eclipse.jetty.server.Server;

class Main {

    public static void main(String[] args) {
        ResourceGrabber grabber = new InternalResourceGrabber("templates");
        WebsiteRules rules = WebsiteRulesBuilder.create().setName("Test - JavalinVC").setUrl("http://127.0.0.1:1234").build();
        ViewManagerBuilder builder = ViewManagerBuilder.create().setExtension(".html").setViewManager("me.kingtux.javalinvc.jtwig.JtwigViewManager");
        Javalin javalin = Javalin.create(javalinConfig -> {
            javalinConfig.server(() -> new Server(1234));
        });
        
        JavalinVC javalinVC = JavalinVCBuilder.create().setJavalin(javalin).
                setResourceGrabber(grabber).setRules(rules)
                .setViewManager(builder).createJavalinVC();


    }
}
``` 

# Creating a Controller
First off you will need to create a new class. 
and you will need to create and object and register it. 

Example: `javalinVC#registerController(new ControllerClass());`

Inside your controller class you will need to put methods with the @Controller annotation
If you want the Javalin Context you can put that as an argument. 
You can also retrieve http params by taking a argument and adding @RequestParam. 
And if you need the View you can add that as argument.

Example
```java
import io.javalin.http.Context;
import me.kingtux.javalinvc.annotations.Controller;
import me.kingtux.javalinvc.annotations.RequestParam;
import me.kingtux.javalinvc.core.RequestType;
import me.kingtux.javalinvc.view.View;

class ControllerClass{
    @Controller(path="/mystuff", requestType = RequestType.GET)
    public void myStuff(Context context,@RequestParam(key = "argument",defaultValue = "hey") String argument, View view){
        //Controller stuff
    }
}
```

