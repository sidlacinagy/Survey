import { Component, OnInit } from '@angular/core';
import {delay} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit{
  results: any;


  constructor(public fb: FormBuilder, private httpClient: HttpClient){ }

 async setResult() {
   let response =await this.httpClient.get<any>('http://localhost:8082/api/result')
     .pipe(delay(500))
     .toPromise();
   console.log(response)
   this.results=response;


 }

  async ngOnInit() {
    this.setResult();
  }



}
