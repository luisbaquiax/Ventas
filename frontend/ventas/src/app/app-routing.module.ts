import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentaComponent } from './components/venta/venta.component';

const routes: Routes = [
  { path: '', redirectTo: '/venta', pathMatch: 'full' },
  {
    path: "venta", component: VentaComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 
  
}
