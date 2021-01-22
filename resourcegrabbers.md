---
layout: default
title: ResourceGrabbers
nav_order: 2
---

### What is a ResourceGrabber?

A ResourceGrabber is a part of this Framework to locate and grab
resource files. 

### Types of ResourceGrabbers?
The Internal Resource Grabber- Grabs files inside the jar only

The External Resource Grabber - Grabs files outside jar only

The Internal External Resource Grabber - Sees if the file outside the
jar exists if not uses the internal one. 

### How to create a ResourceGrabber

```
ResourceGrabbers grabbers = ResourceGrabbers.TYPE;
ResourceGrabber grabber = grabbers.build("resources");
```