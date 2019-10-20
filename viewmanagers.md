---
layout: default
title: View Managers
nav_order: 1
---

### What is a ViewManager?
A ViewManager is a api that allows you to use different templating
engines. We only have one coded into the System and that is Jtwig. 


### How to create a ViewManager
```java
import me.kingtux.javalinvc.view.ViewManagerBuilder;

class Example{
    public static void main(String[] args) {
        ViewManagerBuilder viewManager = 
                ViewManagerBuilder.create().
                        //First set the extension that the files will be using
                        setExtension(".twig").
                        // ANd then set the class that the the ViewManager is ins
                        setViewManager("me.kingtux.javalinvc.jtwig.JtwigViewManager");

    }
}
```
Do not create the ViewManager JavalinVC needs to pass itself to the
final copy.