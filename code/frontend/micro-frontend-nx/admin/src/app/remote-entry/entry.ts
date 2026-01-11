import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  imports: [CommonModule, RouterModule],
  selector: 'app-admin-entry',
  templateUrl: './entry.html',
  styleUrl: './entry.scss',
})
export class RemoteEntry { }
