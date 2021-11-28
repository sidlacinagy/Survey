import {Component, OnInit} from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormBuilder, FormGroup } from "@angular/forms";
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {delay, Observable} from "rxjs";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})




export class AppComponent implements OnInit {
  form: FormGroup;

  constructor(public fb: FormBuilder, private httpClient: HttpClient) {
    this.form = this.fb.group({
      username:"",
      result:""
    })
  }

  ngOnInit() { }

  changeResult(event: { target: any; }) {
    this.form.patchValue({
      result: event.target.value
    });
    // @ts-ignore
    this.form.get('result').updateValueAndValidity()
  }


  changeUser(event: { target: any; }) {
    this.form.patchValue({
      username: event.target.value
    });
    // @ts-ignore
    this.form.get('username').updateValueAndValidity()
  }

  async getSubmitResponse(formData: FormData){
    return this.httpClient.post<any>('http://localhost:8082/api/submit', formData).pipe(delay(1000)).toPromise()
  }

  async submitForm() {
    if (this.form.value.username == "") {
      // @ts-ignore
      document.getElementById("error").innerText = "You must enter a username"
    } else if (this.form.value.result == "") {
      // @ts-ignore
      document.getElementById("error").innerText = "You must choose"
    } else {

      const formData: any = new FormData();
      // @ts-ignore
      formData.append("username", this.form.get('username').value);
      // @ts-ignore
      formData.append("surveyResult", this.form.get('result').value);
      console.log(formData.get("username"));
      console.log(formData.get("surveyResult"));
      let response =await this.httpClient.post<any>('http://localhost:8082/api/submit', formData)
        .pipe(delay(500))
        .toPromise();
      console.log((<String>response));

    }


  }

}

