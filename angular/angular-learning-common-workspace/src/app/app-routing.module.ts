import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LearningsModule} from './learnings/learnings.module';
import { LifeCycleExampleComponent } from './learnings/life-cycle-example/life-cycle-example.component';
const routes: Routes = [
  { path: '',loadChildren: ()=> LearningsModule},
  { path: 'learnings', loadChildren: () => LearningsModule}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
