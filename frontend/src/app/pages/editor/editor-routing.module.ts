import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CageEditorComponent } from './cage-editor/cage-editor.component';
import { PrefillEditorComponent } from './prefill-editor/prefill-editor.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'cages',
    pathMatch: 'full'
  },
  {
    path: 'cages',
    component: CageEditorComponent
  },
  {
    path: 'prefill',
    component: PrefillEditorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EditorRoutingModule { }
