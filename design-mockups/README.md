# Ghorbari - Design Mockups & Prototypes

This folder contains interactive HTML prototypes for the Ghorbari apartment management system.

## 📁 Files

### Interactive Pages
- **INDEX.html** - ⭐ Start here! Complete index of all pages
- **login.html** - Login page with tenant branding
- **dashboard.html** - Admin dashboard with stats, charts, and activities
- **buildings.html** - Buildings management page (grid view)
- **apartments.html** - Apartments/units table with detailed view
- **residents.html** - Resident cards with contact info and lease status

### Documentation
- **README.md** - This file - quick start guide
- **FIGMA_GUIDE.md** - Complete guide for creating Figma designs
- **DESIGN_TOKENS.json** - Design system export (colors, spacing, etc.)

## 🚀 How to View

### Option 1: Open in Browser (Recommended Start)
**Start with INDEX.html** - Double-click to open and explore all pages from there!

Or open any individual HTML file directly in your browser.

### Option 2: Local Server
For a better experience with navigation:

```bash
cd design-mockups

# If you have Python installed:
python -m http.server 8000

# Or with Node.js:
npx http-server -p 8000
```

Then visit: http://localhost:8000/INDEX.html

## 🎨 Design System

### Colors
- **Primary**: #3B82F6 (Blue)
- **Success**: #10B981 (Green)
- **Warning**: #F59E0B (Amber)
- **Danger**: #EF4444 (Red)
- **Gray Scale**: #F9FAFB to #111827

### Typography
- **Font**: Inter (from Google Fonts)
- **Headings**: 600 weight
- **Body**: 400 weight
- **Small text**: 12px

### Spacing
Based on 8px grid system:
- xs: 4px
- sm: 8px
- md: 12px
- lg: 16px
- xl: 24px
- 2xl: 32px

## 📱 Pages Included

### 1. INDEX Page (`INDEX.html`) ⭐
- Beautiful landing page with all mockups
- Quick navigation to all pages
- Design system overview
- Documentation links

### 2. Login Page (`login.html`)
- Clean, centered design
- Email + password authentication
- Remember me checkbox
- Tenant branding placeholder

### 3. Admin Dashboard (`dashboard.html`)
- 4 key metric cards
- Revenue chart placeholder
- Recent activities feed
- Upcoming lease expirations table
- Pending approvals section

### 4. Buildings Page (`buildings.html`)
- Grid view with building cards
- Search and filter functionality
- Building stats (units, occupancy, floors)
- View toggle (Grid/List)
- Add new building card

### 5. Apartments Page (`apartments.html`)
- Comprehensive data table
- Search and multi-filter
- Status badges (Occupied/Vacant/Maintenance)
- Click row to open detail modal
- Pagination

### 5a. Add Apartment Page (`add-apartment.html`)
- Comprehensive form for adding new apartments
- Sections: Basic Info, Unit Details, Financial Info, Amenities, Additional Info
- Form validation (required fields marked with *)
- Amenity checkboxes (Balcony, Parking, Storage, etc.)
- Summary preview card
- Save as Draft and Create buttons

### 6. Residents Page (`residents.html`)
- Beautiful card-based layout
- Resident avatars with initials
- Contact information
- Lease status and dates
- Search and filtering

### 6a. Add Resident Page (`add-resident.html`)
- Comprehensive form for adding new residents
- Sections: Personal Info, Identification, Emergency Contact, Residence Info, Additional Info
- Form validation (required fields marked with *)
- Personal Information: First name, last name, email, phone, date of birth, nationality
- Identification: National ID, passport number, passport expiry
- Emergency Contact: Contact name, phone, relationship
- Residence: Apartment assignment (optional), move-in date, status, primary resident checkbox
- Summary preview card
- Save as Draft and Create buttons

### 7. Create Lease Page (`create-lease.html`)
- Multi-step wizard interface
- Step 1: Unit & Resident Selection
- Unit cards with details and availability
- Resident list with verification status
- Progress indicator
- Search and filter functionality

### 8. Invoices & Payments (`invoices.html`)
- Tabs for Invoices/Payments/Reports
- Invoice table with status badges
- Filter by status and date range
- Detailed invoice modal with:
  - Bill to/from information
  - Line items table
  - Payment history
  - Reminders tracking
- Overdue invoices highlighted

### 9. Resident Dashboard (`resident-dashboard.html`)
- Simplified dashboard for residents
- Lease information card
- Payment status with "Pay Now" button
- Quick actions (Book Amenity, Maintenance, etc.)
- Upcoming bookings management
- Announcements feed

### 10. Amenity Booking (`amenity-booking.html`)
- Grid of available amenities
- Swimming Pool, Fitness Center, Party Hall, Tennis Court, BBQ Area, Playground
- Amenity details (hours, capacity, pricing)
- Booking modal with:
  - Date picker
  - Time slot selection
  - Guest count
  - Additional notes
  - Booking summary
- Tabs for Browse/My Bookings/History

## 🔗 Navigation Flow

```
INDEX.html (Landing page with all mockups)
     ↓
login.html → dashboard.html → buildings.html → apartments.html → residents.html
     ↓              ↓               ↓                ↓                  ↓
  Submit      Sidebar Nav      View Details    Table View        Card View
```

## 🎯 Interactive Features

All pages include:
- ✅ Hover effects on buttons and cards
- ✅ Focus states on inputs
- ✅ Responsive design (mobile-friendly)
- ✅ Click navigation between pages
- ✅ Smooth transitions

## 🛠️ Technologies Used

- **Tailwind CSS** (via CDN) - Utility-first CSS framework
- **Heroicons** (inline SVG) - Icon system
- **Vanilla HTML** - No framework needed

## 📋 Next Steps

### To Create More Pages:
1. Copy an existing HTML file
2. Update the title and content
3. Keep the sidebar and header structure
4. Add to navigation links

### To Customize:
1. Edit the `tailwind.config` section in each file
2. Change colors, fonts, or spacing
3. All styles use Tailwind utility classes

### To Export to Figma:
Follow the detailed guide in `FIGMA_GUIDE.md`

## 💡 Tips

- Use browser DevTools to inspect and modify designs
- Take screenshots for presentations
- Share these files with your team for feedback
- Use as reference when building React components

## 🎨 Design Decisions

### Why Grid View for Buildings?
- Visual hierarchy - easier to scan
- Shows key metrics at a glance
- Better for showcasing building images (future)
- More engaging than table view

### Why Card-Based Layout?
- Modern, clean aesthetic
- Works well on all screen sizes
- Easy to scan and understand
- Follows current design trends

### Why Tailwind CSS?
- Fast prototyping
- Consistent design system
- Easy to customize
- Widely used in production apps

## 📸 Screenshots

Open the files in your browser to see:
- Login page with centered form
- Dashboard with colorful metrics
- Buildings grid with hover effects

## 🤝 Feedback

Use these prototypes to:
- Get stakeholder approval
- Test with users
- Refine the design
- Plan development sprints

## 📚 Additional Resources

- [Tailwind CSS Docs](https://tailwindcss.com)
- [Heroicons](https://heroicons.com)
- [Inter Font](https://fonts.google.com/specimen/Inter)

---

**Note**: These are HTML prototypes for design purposes. The actual application will be built with React/Next.js using the same design system.
