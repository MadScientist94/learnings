import { AfterContentChecked, AfterContentInit, AfterViewChecked, AfterViewInit, Component, DoCheck, OnChanges, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'app-life-cycle-example',
  templateUrl: './life-cycle-example.component.html',
  styleUrls: ['./life-cycle-example.component.css']
})
export class LifeCycleExampleComponent implements OnInit,OnChanges,DoCheck ,AfterContentInit,
AfterContentChecked,
AfterViewInit,
AfterViewChecked,	
OnDestroy
{
 i=0;
 d:any= new Date();
dd:any=new Date();
data:any;
  constructor() { 
    console.log(this.i++ +" => constructor");
  //  this.dd=new Date();
  //   console.log(this.dd-this.d)
this.data=''  

console.log(document.getElementById("lch_main"))
}

update (d:any){
  this.data=d;
console.log(d)
}
  ngOnChanges(){
    console.log(this.i++ +" => ngOnChanges");
    
console.log(document.getElementById("lch_main"))
  }
  ngOnInit(): void {
    console.log(this.i++ +" => ngOnInit");
    // alert('working');
    
console.log(document.getElementById("lch_main"))
  }
ngDoCheck(){
  console.log(this.i++ +" => ngDoCheck");
  
console.log(document.getElementById("lch_main"))
}
ngAfterContentInit(){
  console.log(this.i++ +" => ngAfterContentInit");
  
console.log(document.getElementById("lch_main"))
}

ngAfterContentChecked(){

  console.log(this.i++ +" => ngAfterContentChecked");

  console.log(document.getElementById("lch_main"))
}
ngAfterViewInit()	{

  console.log(this.i++ +" => ngAfterViewInit");

  console.log(document.getElementById("lch_main"))
}
ngAfterViewChecked()	{

  console.log(this.i++ +" => ngAfterViewChecked");

  console.log(document.getElementById("lch_main"))
}
ngOnDestroy(){

  console.log(this.i++ +" => ngOnDestroy");

  console.log(document.getElementById("lch_main"))
}






}
