import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

import { EditorRoutingModule } from './editor-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { CageEditorComponent } from './cage-editor/cage-editor.component';
import { PrefillEditorComponent } from './prefill-editor/prefill-editor.component';


@NgModule({
  declarations: [
    CageEditorComponent,
    PrefillEditorComponent
  ],
  imports: [
    CommonModule,
    EditorRoutingModule,
    MatButtonModule,
    SharedModule
  ]
})
export class EditorModule { }
