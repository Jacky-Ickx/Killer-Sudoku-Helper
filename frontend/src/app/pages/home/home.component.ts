import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent {
    id: string = '';

    constructor(private router: Router) { }

    enterGame() {
        console.debug(this.id);

        this.router.navigate(['sudoku', this.id]).then(nav => {
            console.debug(nav);
        }).catch(err => {
            console.error(err);
        });
    }
}
