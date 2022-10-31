# clipper-MS
Clipper management system


#### Clipper, series and collected clippers

- Each AppUser has a list (collection) of CollectedClippers.
- Collected clipper holds user specific information
(date and location bought, notes).
- Clippers only get Created, Updated and Deleted by admins
(creator is saved in clipper as createdBy).
- All users are capable of CRUD their own CollectedClippers
-> does not affect Clippers themselves. 
- Original series follow standard series rules (Name = series name, max number = 4).
Custom series need guidelines (e.g. [city]-shops, [brand]-lighters, etc.) 
No series should be mapped to default series 'no-series' (currently seriesId = null).
- Users can create lists to group their collected clippers on their profile
-> user specific. 

###### Group format:
Id \
UserId \
Name \
Description \
Clippers

