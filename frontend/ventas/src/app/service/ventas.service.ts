import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Venta } from '../objetos/Venta';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class VentasService {
  url: String = 'http://localhost:8080/VentasApi/ControllerData?tarea=';

  constructor(private http: HttpClient) {}

  public getVentas(): Observable<Venta[]> {
    return this.http.get<Venta[]>(this.url + 'ventas');
  }
}
