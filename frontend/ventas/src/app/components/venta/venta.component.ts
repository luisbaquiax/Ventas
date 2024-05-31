import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { VentasService } from 'src/app/service/ventas.service';
import { Venta } from 'src/app/objetos/Venta';

@Component({
  selector: 'app-venta',
  templateUrl: './venta.component.html',
  styleUrls: ['./venta.component.css'],
})
export class VentaComponent implements OnInit {
  file: File | undefined;
  url: string =
    'http://localhost:8080/VentasApi-1.0-SNAPSHOT/ControllerData?tarea=subir';
  menssage: String = '';

  ventas: Venta[] = [];

  cantidadVentas: number = 0;
  unidadesVendidas: number = 0;

  constructor(private http: HttpClient, private serviceVenta: VentasService) {
    this.serviceVenta.getVentas().subscribe((lista) => {
      this.ventas = lista;
    });
  }

  ngOnInit(): void {}

  chooseFile(event: any) {
    this.file = event.target.files[0];
  }

  subirDatos() {
    console.log('hola sendfile');
    if (!this.file) {
      return;
    }

    const form = new FormData();

    form.append('archivo', this.file);
    this.http.post(this.url, form).subscribe((response) => {
      this.menssage = 'Se ha procesado correctamente el archivo.';
      console.log('Se ha enviado el archivo. ', response);
    });
  }

  setNumbers(){
    this.ventas.forEach(element => {
      this.unidadesVendidas += element.cantidad;
    });
    this.cantidadVentas = this.ventas.length;
  }

}