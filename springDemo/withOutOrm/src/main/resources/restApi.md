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

select by condition <br> http://localhost:8080/ <br> body contains any/all the following fields
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