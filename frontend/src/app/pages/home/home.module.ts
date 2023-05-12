import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

import { HomeRoutingModule } from './home-routing.module';
import { HomeComponent } from './home.component';
import { SharedModule } from "../../shared/shared.module";


@NgModule({
    declarations: [
        HomeComponent
    ],
    imports: [
        CommonModule,
        MatButtonModule,
        HomeRoutingModule,
        SharedModule
    ]
})
export class HomeModule { }
