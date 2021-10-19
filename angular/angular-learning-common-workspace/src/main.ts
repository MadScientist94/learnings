import { style } from '@angular/animations';
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
  
// console.log(document.getElementById("example")?.style.height);
var x = document.getElementsByClassName("example");
var i;
var h= screen.height
const element = document.getElementsByTagName('html');
element[0].setAttribute("style",`height:${h}px;border:0px;padding:0px;`)
console.log(element[0].getAttribute("style"))


for (i = 0; i < x.length; i++) {
  // x[i].style.backgroundColor = "red";
}

document.getElementById("example")?.style.color;
  