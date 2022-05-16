import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { Product } from '../dataModels/product';
import { Genre } from '../dataModels/genre';
import { Author } from '../dataModels/author';
import { ProductService } from '../services/product.service';
import { GenreService } from '../services/genre.service';
import { AuthorService } from '../services/author.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import {map, startWith} from 'rxjs/operators';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.scss']
})
export class AddProductComponent implements OnInit {

  authors: Author[] = [];
  genres: Genre[] = [];
  genreNames: string[] = [];

  productForm: FormGroup = new FormGroup({});

  addedGenres: Genre[] = [];
  addedAuthors: Author[] = [];

  loading: boolean = false;
  failed: boolean = false;

  filteredOptionsGenre: Observable<string[]>;
  filteredOptionsAuthor: Author[];

  constructor(public dialog: MatDialogRef<AddProductComponent>,
                private productService: ProductService,
                private genreService: GenreService,
                private authorService: AuthorService) {

      this.productForm = new FormGroup({
        isbn: new FormControl('', Validators.required),
        title: new FormControl('', Validators.required),
        authorFirstName: new FormControl('', Validators.required),
        authorLastName: new FormControl('', Validators.required),
        authorBirth: new FormControl('', Validators.required),
        publisher: new FormControl('', Validators.required),
        publication: new FormControl('', Validators.required),
        genre: new FormControl('', Validators.required),
        copies: new FormControl('', Validators.required)
      });

      this.getGenres();
      this.getAuthors();
  }

  closeDialog(): void {
    this.dialog.close();
  }

  addProduct(): void {
    this.loading = true;
    let product: Product = {
      isbn: this.productForm.get('isbn')!.value,
      title: this.productForm.get('title')!.value,
      publisher: this.productForm.get('publisher')!.value,
      publication: this.productForm.get('publication')!.value,
      copies: this.productForm.get('copies')!.value
    }

    this.addProductInDb(product);
  }

  canCreate(): boolean {
    return ( (this.productForm.get('isbn')!.value.length > 0 ) &&
             (this.productForm.get('title')!.value.length > 0) &&
             (this.productForm.get('publisher')!.value.length > 0) &&
             (this.productForm.get('publication')!.value.length > 0) &&
             (this.addedGenres.length > 0) &&
             (this.addedAuthors.length > 0) &&
             (this.productForm.get('copies')!.value > 0) );
  }

  ngOnInit(): void {
      this.filteredOptionsGenre = this.productForm.get('genre')!.valueChanges.pipe(
        startWith(''),
        map(value => this._filterGenre(value))
      );
  }

  private _filterGenre(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.genreNames.filter(option => option.toLowerCase().includes(filterValue));
  }

  public updateFilteredAuthors(): void {
    let firstName = this.productForm.get('authorFirstName')!.value;
    let lastName = this.productForm.get('authorLastName')!.value;

    this.filteredOptionsAuthor = this.authors.filter(option => (
            (option.firstName.includes(firstName)) &&
            (option.lastName.includes(lastName))
            )
          );
  }

  public addProductInDb(p: Product): void {
    let addGenreIds: number[] = [];
    this.addedGenres.filter(g => g.id != null).forEach(g => addGenreIds.push(g.id!));
    let addGenreNames: string[] = [];
    this.addedGenres.filter(g => g.id == null).forEach(g => addGenreNames.push(g.name));

    let addAuthorIds: number[] = [];
    this.addedAuthors.filter(a => a.id != null).forEach(a => addAuthorIds.push(a.id!));
    let addAuthorFirstNames: string[] = [];
    let addAuthorLastNames: string[] = [];
    let addAuthorBirths: Date[] = [];
    this.addedAuthors.filter(a => a.id == null).forEach(a => {
      addAuthorFirstNames.push(a.firstName);
      addAuthorLastNames.push(a.lastName);
      addAuthorBirths.push(a.birth);
    });

    this.productService.addProduct(p, addGenreIds, addGenreNames, addAuthorIds, addAuthorFirstNames, addAuthorLastNames, addAuthorBirths).subscribe(
      (response: any) => {
        console.log(`${response} added product`);
        this.closeDialog();
      },
      (error: HttpErrorResponse) => {
        this.loading = false;
        this.failed = true;
      }
     );
  }

  public getAuthors(): void {
    this.authorService.getAuthors().subscribe(
      (response: Author[]) => {
        this.authors = response;
        console.log(this.authors);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
     );
  }

  public getGenres(): void {
    this.genreService.getGenres().subscribe(
      (response: Genre[]) => {
        this.genres = response;
        this.genreNames = [];
        this.genres.forEach(g => this.genreNames.push(g.name));
        console.log(this.genres);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
     );
  }

  canAddGenre(): boolean {
    let g: string = this.productForm.get('genre')!.value;
    return (
      (g.length > 0) &&
      (!this.listContainsGenre(this.addedGenres, g))
     );
  }

  canAddAuthor(): boolean {

    let fn: string = this.productForm.get('authorFirstName')!.value;
    let ln: string = this.productForm.get('authorLastName')!.value;
    let b: Date = this.productForm.get('authorBirth')!.value;
    return (
      (fn.length > 0) &&
      (ln.length > 0) &&
      (this.productForm.get('authorBirth')!.value.length > 0) &&
      (!this.listContainsAuthor(this.addedAuthors, fn, ln, b))
     );
  }

  listContainsGenre(list: Genre[], name: string): boolean {
    let ret = false;
    list.forEach(g => {
      if (g.name === name) {
        ret = true;
      }
    });
    return ret;
  }

  listContainsAuthor(list: Author[], fn: string, ln: string, b: Date): boolean {
    let ret = false;
    list.forEach(a => {
      if ( (a.firstName === fn) && (a.lastName === ln) && (a.birth === b) ) {
        ret = true;
      }
    });
    return ret;
  }

  addGenre(): void {
    let genre = this.genres.find(g => g.name === this.productForm.get('genre')!.value);
    if (genre != null) {
      this.addedGenres.push(genre);
    }
    else {
      genre = {name: this.productForm.get('genre')!.value};
      this.addedGenres.push(genre);
    }
    this.productForm.get('genre')!.setValue("");
    console.log(this.addedGenres);
  }

  addAuthor(): void {
    let fn = this.productForm.get('authorFirstName')!.value;
    let ln = this.productForm.get('authorLastName')!.value;
    let b = this.productForm.get('authorBirth')!.value;
    let author = this.authors.find(a => ( (a.firstName === fn) && (a.lastName === ln) && (a.birth === b) ));
    if (author != null) {
      this.addedAuthors.push(author);
    }
    else {
      author = {firstName: fn, lastName: ln, birth: b};
      this.addedAuthors.push(author);
    }
    this.productForm.get('authorFirstName')!.setValue("");
    this.productForm.get('authorLastName')!.setValue("");
    this.productForm.get('authorBirth')!.setValue("");
    console.log(this.addedAuthors);
  }

  removeGenre(genre: Genre): void {
    this.addedGenres = this.addedGenres.filter(g => g != genre);
  }

  removeAuthor(author: Author): void {
    this.addedAuthors = this.addedAuthors.filter(a => a != author);
  }

  setAuthorData(author: Author): void {
    this.productForm.get('authorFirstName')!.setValue(author.firstName);
    this.productForm.get('authorLastName')!.setValue(author.lastName);
    this.productForm.get('authorBirth')!.setValue(author.birth);
  }

}
