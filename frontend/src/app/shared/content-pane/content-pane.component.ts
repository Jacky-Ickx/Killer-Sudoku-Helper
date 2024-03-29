import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-content-pane',
  templateUrl: './content-pane.component.html',
  styleUrls: ['./content-pane.component.scss']
})
export class ContentPaneComponent {
    @Input() centered: boolean = false;
}
