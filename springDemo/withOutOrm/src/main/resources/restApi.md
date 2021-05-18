## learnings
### basic concepts about annotaions 
1. @component
2. @controller
3. @service
4. @repositiory
5. @configure
6. @bean
7. @qualifier
8. @RestController
9. @GetMapping
10. @postMapping
11. @putmaping
12. @deletemapping
13. @pathvariable
14. @RequestBody



## select all students
<a name="js">json request body Struture</a>
```json
    {
        'data': {
            'roll_no' : 'rollNo',
	        'name' : 'name',
        	'department' : 'department',
	        'email' : 'email',
    	    'address' : 'address',
    	    'phone' : 'phone',
            'm1':1,
            'm2':2,
            'm3':3,
            'm4':4,
            'm5':5
            },
        'condition': {
            'roll_no' : 'rollNo',
	        'name' : 'name',
        	'department' : 'department',
	        'email' : 'email',
    	    'address' : 'address',
    	    'phone' : 'phone',
            'm1':1,
            'm2':2,
            'm3':3,
            'm4':4,
            'm5':5
            }
```

API

select all<br>
GET ==> http://localhost:8081/

select by rollno
<br>GET ==> http://localhost:8081/12343

select by condition <br>
POST ==> http://localhost:8081/where <br> 
body contains any/all the fields in [condition json](#condition_json)

insert data<br>
POST ==> http://localhost:8081/
body contains [data objects](#do)

update data by roll_no<br>
PUT ==> http://localhost:8081/12343<br> 
body contains [data objects](#do)

update data by where condition<br>
PUT ==> http://localhost:8081/where<br> 
body contains all the [entire json Structure](#js) and any/all the fields in data object and condition object

delete data by roll_no<br>
Delete ==> http://localhost:8081/12343<br> 

update data by where condition<br>
Delete ==> http://localhost:8081/where<br> 
body contains any/all the fields in [condition json](#condition_json)










## <a name="do"></a> data json
```json
    {
        'data': {
            'roll_no' : 'rollNo',
	        'name' : 'name',
        	'department' : 'department',
	        'email' : 'email',
    	    'address' : 'address',
    	    'phone' : 'phone',
            'm1':1,
            'm2':2,
            'm3':3,
            'm4':4,
            'm5':5
            }
    }
```
## <a name="condition_json"> condition json </a>
```json
    {
        'condition': {
            'roll_no' : 'rollNo',
	        'name' : 'name',
        	'department' : 'department',
	        'email' : 'email',
    	    'address' : 'address',
    	    'phone' : 'phone',
            'm1':1,
            'm2':2,
            'm3':3,
            'm4':4,
            'm5':5
            }
```