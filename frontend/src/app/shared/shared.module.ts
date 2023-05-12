import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { ContentPaneComponent } from './content-pane/content-pane.component';
import { DividerComponent } from './divider/divider.component';



@NgModule({
  declarations: [
    ContentPaneComponent,
    DividerComponent
  ],
  imports: [
    CommonModule,
    MatDividerModule
  ],
  exports: [
    ContentPaneComponent,
    DividerComponent
  ]
})
export class SharedModule { }
