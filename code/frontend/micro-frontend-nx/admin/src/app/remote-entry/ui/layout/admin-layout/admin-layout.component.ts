import { Component, ViewEncapsulation } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AdminSidebarComponent } from '../sidebar/sidebar.component';
import { AdminHeaderComponent } from '../header/header.component';
import { AdminFooterComponent } from '../footer/footer.component';

@Component({
    selector: 'app-admin-layout',
    standalone: true,
    imports: [CommonModule, RouterModule, AdminSidebarComponent, AdminHeaderComponent, AdminFooterComponent],
    templateUrl: './admin-layout.component.html',
    encapsulation: ViewEncapsulation.None
})
export class AdminLayoutComponent { }
