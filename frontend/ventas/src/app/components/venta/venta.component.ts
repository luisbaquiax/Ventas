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
  url: string = 'http://localhost:8080/VentasApi/ControllerData';
  menssage: String = '';

  ventas: Venta[] = [];

  cantidadVentas: number = 0;
  unidadesVendidas: number = 0;

  constructor(private http: HttpClient, private serviceVenta: VentasService) {
    this.serviceVenta.getVentas().subscribe((lista) => {
      this.ventas = lista;
    });
    this.setNumbers();
  }

  ngOnInit(): void {
    this.serviceVenta.getVentas().subscribe((lista) => {
      this.ventas = lista;
    });
    this.setNumbers();
  }

  chooseFile(event: any) {
    this.file = event.target.files[0];
  }

  subirDatos() {
    console.log('hola sendfile');
    if (!this.file) {
      this.menssage = 'Debes elegir el archivo antes de subir los datos';
      return;
    }

    const form = new FormData();

    form.append('archivo', this.file);
    this.http.post(this.url, form).subscribe((response) => {
      this.menssage = 'Se ha procesado correctamente el archivo.';
      console.log('Se ha enviado el archivo. ', response);
    });
  }

  setNumbers() {
    this.unidadesVendidas = 0;
    this.ventas.forEach((element) => {
      this.unidadesVendidas += element.cantidad;
    });
    this.cantidadVentas = this.ventas.length;
  }

  verDatos() {
    this.setNumbers();
  }
}
