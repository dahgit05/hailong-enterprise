import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.scss',
})
export class MainLayoutComponent {
  currentYear = new Date().getFullYear();

  // Navigation items for sidebar
  navItems = [
    { path: '/factory', icon: 'precision_manufacturing', title: 'Nhà máy', active: false },
    { path: '/hrm', icon: 'badge', title: 'Nhân sự', active: false },
    { path: '/machine', icon: 'settings_input_component', title: 'Máy móc', active: false },
  ];

  // Module cards for dashboard
  moduleCards = [
    {
      path: '/hrm',
      icon: 'badge',
      title: 'Nhân sự (HRM)',
      description: 'Quản lý định biên, lương & phúc lợi',
      bgColor: 'bg-orange-50',
      iconColor: 'text-orange-500',
      isActive: false,
    },
    {
      path: '/machine',
      icon: 'precision_manufacturing',
      title: 'Máy móc & Thiết bị',
      description: 'Tài sản cố định & Giám sát IoT',
      bgColor: 'bg-primary',
      iconColor: 'text-white',
      isActive: true,
    },
    {
      path: '/factory',
      icon: 'domain',
      title: 'Nhà máy Thông minh',
      description: 'Vận hành sản xuất & Chuỗi cung ứng',
      bgColor: 'bg-blue-50',
      iconColor: 'text-blue-500',
      isActive: false,
    },
  ];
}
