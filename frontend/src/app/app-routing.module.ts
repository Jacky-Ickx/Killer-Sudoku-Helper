import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './pages/page-not-found/page-not-found.component';
import { ImprintComponent } from './pages/imprint/imprint.component';
import { RulesComponent } from './pages/rules/rules.component'
import { StrategiesComponent } from './pages/strategies/strategies.component';

const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./pages/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'editor',
    loadChildren: () => import('./pages/editor/editor.module').then(m => m.EditorModule)
  },
  {
    path: 'sudoku',
    loadChildren: () => import('./pages/killer-sudoku/killer-sudoku.module').then(m => m.KillerSudokuModule)
  },
  {
    path: 'imprint',
    component: ImprintComponent
  },
  {
    path: 'rules',
    component: RulesComponent
  },
  {
    path: 'strategies',
    component: StrategiesComponent
  },
  {
    path: '404',
    component: PageNotFoundComponent
  },
  {
    path: '**',
    redirectTo: '404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
