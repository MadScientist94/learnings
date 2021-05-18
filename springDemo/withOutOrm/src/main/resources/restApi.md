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
json request body Struture
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
http://localhost:8080/

select by rollno
<br> http://localhost:8080/12343

select by condition <br> http://localhost:8080/ <br> body contains any/all the fields in [condition json](#condition_json)

a
a
a
a
a

a
a
a


a
a
a


a

a
a

a

a

a

a

a

a

a

a

a













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