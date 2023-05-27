import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './core/header/header.component';
import { FooterComponent } from './core/footer/footer.component';
import { RulesComponent } from './pages/rules/rules.component';
import { StrategiesComponent } from './pages/strategies/strategies.component';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { ImprintComponent } from './pages/imprint/imprint.component';
import { SharedModule } from './shared/shared.module';
import { HttpClientModule } from '@angular/common/http';
import { RxStompService, rxStompServiceFactory } from './rx-stomp.service';

@NgModule({
	declarations: [
		AppComponent,
		HeaderComponent,
		FooterComponent,
		RulesComponent,
		StrategiesComponent,
		PageNotFoundComponent,
		ImprintComponent
	],
	imports: [
		BrowserModule,
		BrowserAnimationsModule,
		AppRoutingModule,
		SharedModule,
		HttpClientModule
	],
	providers: [
		{
			provide: RxStompService,
			useFactory: rxStompServiceFactory
		}
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
