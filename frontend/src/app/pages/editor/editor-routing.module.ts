import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CageEditorComponent } from './cage-editor/cage-editor.component';
import { PrefillEditorComponent } from './prefill-editor/prefill-editor.component';
import { CageEditorCanDeactivateGuard } from './cage-editor/deactivate.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'cages',
    pathMatch: 'full'
  },
  {
    path: 'cages',
    component: CageEditorComponent,
    canDeactivate: [CageEditorCanDeactivateGuard]
  },
  {
    path: 'prefill',
    component: PrefillEditorComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [CageEditorCanDeactivateGuard]
})
export class EditorRoutingModule { }
