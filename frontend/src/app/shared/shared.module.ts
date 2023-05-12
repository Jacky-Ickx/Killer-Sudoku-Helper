import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ContentPaneComponent } from './content-pane/content-pane.component';



@NgModule({
  declarations: [
    ContentPaneComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ContentPaneComponent
  ]
})
export class SharedModule { }
