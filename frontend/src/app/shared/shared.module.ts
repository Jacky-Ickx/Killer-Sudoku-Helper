import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { ContentPaneComponent } from './content-pane/content-pane.component';
import { DividerComponent } from './divider/divider.component';
import { CellComponent } from './cell/cell.component';
import { GridComponent } from './grid/grid.component';
import { MatInputModule} from '@angular/material/input';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatFormFieldModule } from '@angular/material/form-field';

@NgModule({
  declarations: [
    ContentPaneComponent,
    DividerComponent,
    CellComponent,
    GridComponent
  ],
  imports: [
    CommonModule,
    MatDividerModule,
    MatInputModule,
    MatGridListModule,
    MatFormFieldModule
  ],
  exports: [
    ContentPaneComponent,
    DividerComponent,
    CellComponent,
    GridComponent
  ]
})
export class SharedModule { }
