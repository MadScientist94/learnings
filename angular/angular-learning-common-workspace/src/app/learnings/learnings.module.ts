import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LifeCycleExampleComponent } from './life-cycle-example/life-cycle-example.component';
import { LearningRoutingModule } from './learning-routing.module';



@NgModule({
  declarations: [
    LifeCycleExampleComponent
  ],
  imports: [
    CommonModule,
    LearningRoutingModule,
  ]
})
export class LearningsModule { }
