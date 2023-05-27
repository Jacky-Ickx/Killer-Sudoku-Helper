import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

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
        SharedModule,
        MatButtonModule,
        MatInputModule,
        MatFormFieldModule,
        MatIconModule,
        MatProgressSpinnerModule,
        ReactiveFormsModule
    ]
})
export class EditorModule { }
