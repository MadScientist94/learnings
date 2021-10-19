import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LifeCycleExampleComponent } from './life-cycle-example/life-cycle-example.component';

const routes: Routes = [
  { path: 'lifecycle', component:LifeCycleExampleComponent},
  { path: '', component:LifeCycleExampleComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LearningRoutingModule { }
