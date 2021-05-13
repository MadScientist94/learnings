create application
student/create? name=j,std=1
read application
update application
delete application


@RestController => these annotation mentions that the class is a controller that can handle http request
@GetMapping("/greeting")=> The @GetMapping annotation ensures that HTTP GET requests to /greeting are mapped to the greeting() method.
```java
@RestController//mentioning that the class gonna handle the http request
public class GreetingController {
	@GetMapping("/greeting")//it is the http request path from the server port
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        //requestparams uses to get the value
        //value in request params mention the key of the get request parameter and 
        //set the request parameter to the variable declared after requestparams method
		return new Greeting(String.format(template, name));
        //return is a greeting class constructor
	}
}
```

alternative way [@RequestMapping(method=GET)]
```java
@RestController
@RequestMapping("/home")
public class IndexController {
    @RequestMapping(method = RequestMethod.GET)
    String get() {
        return "Hello from get";
    }
    @RequestMapping(method = RequestMethod.DELETE)
    String delete() {
        return "Hello from delete";
    }
    @RequestMapping(method = RequestMethod.POST)
    String post() {
        return "Hello from post";
    }
    @RequestMapping(method = RequestMethod.PUT)
    String put() {
        return "Hello from put";
    }
    @RequestMapping(method = RequestMethod.PATCH)
    String patch() {
        return "Hello from patch";
    }
}
```
