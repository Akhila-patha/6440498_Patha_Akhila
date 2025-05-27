// Document Ready
document.addEventListener('DOMContentLoaded', function() {
    console.log('Welcome to the Community Portal');
    initApp();
});  

// Global Variables
let events = [
    {
        id: 1,
        title: "Community Picnic",
        date: "2025-08-15",
        time: "12:00 PM",
        location: {
            name: "Central Park",
            lat: 40.7829,
            lng: -73.9654
        },
        category: "social",
        seats: 50,
        available: 35,
        description: "Join us for our annual community picnic with food, games, and fun activities for all ages.",
        image: "images/upcoming.jpg"
    },
    {
        id: 2,
        title: "Summer Music Festival",
        date: "2025-08-20",
        time: "6:00 PM",
        location: {
            name: "Town Square",
            lat: 40.7580,
            lng: -73.9855
        },
        category: "music",
        seats: 200,
        available: 45,
        description: "Local bands and musicians performing throughout the evening. Food trucks will be available.",
        image: "images/upcoming.jpg"
    },
    {
        id: 3,
        title: "Gardening Workshop",
        date: "2025-08-25",
        time: "10:00 AM",
        location: {
            name: "Community Garden",
            lat: 40.7321,
            lng: -73.9915
        },
        category: "workshop",
        seats: 30,
        available: 12,
        description: "Learn sustainable gardening techniques from our local experts. Perfect for beginners.",
        image: "images/upcoming.jpg"
    },
    {
        id: 4,
        title: "Basketball Tournament",
        date: "2025-09-02",
        time: "9:00 AM",
        location: {
            name: "Community Center Gym",
            lat: 40.7465,
            lng: -73.9818
        },
        category: "sports",
        seats: 100,
        available: 78,
        description: "Annual 3-on-3 basketball tournament for all skill levels. Sign up your team today!",
        image: "images/upcoming.jpg"
    },
    {
        id: 5,
        title: "Book Club Meeting",
        date: "2025-09-10",
        time: "7:00 PM",
        location: {
            name: "Public Library",
            lat: 40.7536,
            lng: -73.9822
        },
        category: "social",
        seats: 25,
        available: 5,
        description: "This month we're discussing 'The Midnight Library' by Matt Haig. New members welcome!",
        image: "images/upcoming.jpg"
    },
    {
        id: 6,
        title: "Yoga in the Park",
        date: "2025-09-16",
        time: "8:00 AM",
        location: {
            name: "Riverside Park",
            lat: 40.7990,
            lng: -73.9685
        },
        category: "sports",
        seats: 40,
        available: 40,
        description: "Start your weekend with a relaxing yoga session in the beautiful Riverside Park.",
        image:"images/upcoming.jpg"
    }
];

let registrations = [];
let userPreferences = {};
let map;
let userMarker;

// Initialize the application
function initApp() {
    loadUserPreferences();
    displayEvents(events);
    populateEventDropdown();
    setupEventListeners();
    setupGeolocationFeature();

    setTimeout(() => {
        alert('Page has finished loading. Welcome to our Community Portal!');
    }, 500);
}

// Load user preferences from localStorage
function loadUserPreferences() {
    const savedPrefs = localStorage.getItem('communityPortalPrefs');
    if (savedPrefs) {
        userPreferences = JSON.parse(savedPrefs);
        console.log('Loaded user preferences:', userPreferences);
    }
}

// Save user preferences to localStorage
function saveUserPreferences() {
    localStorage.setItem('communityPortalPrefs', JSON.stringify(userPreferences));
    console.log('Saved user preferences:', userPreferences);
}

// Calculate distance between two coordinates in km
function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371;
    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;
    const a = 
        Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) * 
        Math.sin(dLon/2) * Math.sin(dLon/2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    return R * c;
}

// Display events in the UI
function displayEvents(eventsToDisplay) {
    const eventsContainer = document.getElementById('eventsContainer');
    eventsContainer.innerHTML = '';

    if (eventsToDisplay.length === 0) {
        eventsContainer.innerHTML = '<div class="col-12 text-center py-4"><p class="text-muted">No events found matching your criteria.</p></div>';
        return;
    }

    eventsToDisplay.forEach(event => {
        const today = new Date();
        const eventDate = new Date(event.date);

        if (event.available > 0 && eventDate >= today) {
            const eventCard = createEventCard(event);
            eventsContainer.appendChild(eventCard);
        }
    });
}

// Create an event card element
function createEventCard(event) {
    const col = document.createElement('div');
    col.className = 'col-md-6 col-lg-4';

    const card = document.createElement('div');
    card.className = 'card h-100 event-card shadow-sm';

    const img = document.createElement('img');
    img.src = event.image || 'images/event-default.png';
    img.alt = event.title;
    img.className = 'card-img-top event-img';

    const cardBody = document.createElement('div');
    cardBody.className = 'card-body';

    const title = document.createElement('h5');
    title.className = 'card-title';
    title.textContent = event.title;

    const dateTime = document.createElement('p');
    dateTime.className = 'card-text';
    dateTime.textContent = `${event.date} at ${event.time}`;

    const location = document.createElement('p');
    location.className = 'card-text';
    location.textContent = `Location: ${event.location.name}`;

    const desc = document.createElement('p');
    desc.className = 'card-text';
    desc.textContent = event.description;

    const seatsInfo = document.createElement('p');
    seatsInfo.className = 'card-text text-success';
    seatsInfo.textContent = `Available Seats: ${event.available}`;

    cardBody.appendChild(title);
    cardBody.appendChild(dateTime);
    cardBody.appendChild(location);
    cardBody.appendChild(desc);
    cardBody.appendChild(seatsInfo);
    card.appendChild(img);
    card.appendChild(cardBody);
    col.appendChild(card);

    return col;
}

// Populate dropdown
function populateEventDropdown() {
    const eventSelect = document.getElementById('event');
    eventSelect.innerHTML = '<option value="" selected disabled>Choose an event...</option>';

    events.forEach(event => {
        if (event.available > 0) {
            const option = document.createElement('option');
            option.value = event.id;
            option.textContent = `${event.title} (${event.available} seats left)`;
            eventSelect.appendChild(option);
        }
    });
}

// Setup event listeners
function setupEventListeners() {
    const form = document.getElementById('registrationForm');
    if (!form) return;

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const selectedEventId = parseInt(document.getElementById('event').value);
        const selectedEvent = events.find(ev => ev.id === selectedEventId);

        if (selectedEvent && selectedEvent.available > 0) {
            selectedEvent.available -= 1;

            const modal = new bootstrap.Modal(document.getElementById('registrationModal'));
            document.getElementById('modalMessage').textContent = `You've successfully registered for ${selectedEvent.title}!`;
            modal.show();

            displayEvents(events);
            populateEventDropdown();
            form.reset();
            updateCharCount(); // Reset count after form reset

            alert('Thank you! Your registration is confirmed.'); // ✅ onclick confirmation
        } else {
            alert('Sorry, no seats available.');
        }
    });
    // 🔍 Search functionality
if (searchForm && searchInput) {
    searchForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const query = searchInput.value.trim().toLowerCase();

        if (!query) {
            displayEvents(events); // Show all events when input is empty
            return;
        }

        const filteredEvents = events.filter(event =>
            event.title.toLowerCase().includes(query) ||
            event.description.toLowerCase().includes(query) ||
            event.category.toLowerCase().includes(query) ||
            event.location.name.toLowerCase().includes(query)
        );

        displayEvents(filteredEvents);
    });

    // 🔄 Automatically show all events when user clears the search box
    searchInput.addEventListener('input', () => {
        if (searchInput.value.trim() === '') {
            displayEvents(events);
        }
    });
}
const clearSearchBtn = document.getElementById('clearSearchBtn');
if (clearSearchBtn) {
    clearSearchBtn.addEventListener('click', () => {
        searchInput.value = '';
        displayEvents(events); // Reset to all events
    });
}

    // ✅ Character counter for "Additional Comments"
    const commentsInput = document.getElementById('comments');
    const charCount = document.getElementById('charCount');

    function updateCharCount() {
        if (commentsInput && charCount) {
            charCount.textContent = `${commentsInput.value.length} characters`;
        }
    }

    if (commentsInput && charCount) {
        commentsInput.addEventListener('input', updateCharCount);
        commentsInput.addEventListener('keyup', updateCharCount); // ✅ keyup version
        updateCharCount();
    }

    // ✅ onblur validation for phone number
    const phoneInput = document.getElementById('phone');
    if (phoneInput) {
        phoneInput.addEventListener('blur', () => {
            const phone = phoneInput.value.trim();
            const pattern = /^[6-9]\d{9}$/;
            phoneInput.classList.remove('is-invalid');

            if (!pattern.test(phone)) {
                phoneInput.classList.add('is-invalid');
                if (!phoneInput.nextElementSibling || !phoneInput.nextElementSibling.classList.contains('invalid-feedback')) {
                    const error = document.createElement('div');
                    error.className = 'invalid-feedback';
                    error.textContent = 'Please enter a valid 10-digit phone number starting with 6-9.';
                    phoneInput.parentElement.appendChild(error);
                }
            }
        });
    }

    // ✅ onchange to display event fee
    const eventSelect = document.getElementById('event');
    if (eventSelect) {
        let feeDisplay = document.createElement('p');
        feeDisplay.className = 'text-muted mt-2';
        eventSelect.parentElement.appendChild(feeDisplay);

        eventSelect.addEventListener('change', () => {
            const selectedId = parseInt(eventSelect.value);
            const selectedEvent = events.find(e => e.id === selectedId);
            if (selectedEvent) {
                const fee = 100 + selectedEvent.id * 10; // Example fee calculation
                feeDisplay.textContent = `Event Fee: ₹${fee}`;
            } else {
                feeDisplay.textContent = '';
            }
        });
    }

    // ✅ ondblclick to enlarge images
    document.querySelectorAll('.gallery-img').forEach(img => {
        img.addEventListener('dblclick', () => {
            img.classList.toggle('enlarged');
            img.style.transition = 'transform 0.3s ease';
            img.style.transform = img.classList.contains('enlarged') ? 'scale(1.5)' : 'scale(1)';
        });
    });
    
}
function videoReady() {
    const statusEl = document.getElementById('videoStatus');
    if (statusEl) {
        statusEl.textContent = '🎬 Video ready to play!';
    }
}
// 📝 Feedback form character counter
const feedbackInput = document.getElementById('feedbackText');
const feedbackCharCount = document.getElementById('feedbackCharCount');
const feedbackForm = document.getElementById('feedbackForm');

if (feedbackInput && feedbackCharCount) {
    function updateFeedbackCharCount() {
        feedbackCharCount.textContent = `${feedbackInput.value.length} characters`;
    }

    feedbackInput.addEventListener('input', updateFeedbackCharCount);
    feedbackInput.addEventListener('keyup', updateFeedbackCharCount);
    updateFeedbackCharCount();
}

// ✅ Optional: Handle form submission
if (feedbackForm) {
    feedbackForm.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Thank you for your feedback!');
        feedbackForm.reset();
        updateFeedbackCharCount();
    });
}

// Contact form fake submission
const contactForm = document.querySelector('#contact form');
if (contactForm) {
    contactForm.addEventListener('submit', function(e) {
        e.preventDefault();
        alert('Thanks for contacting us! We will get back to you soon.');
        contactForm.reset();
    });
}
// 🎯 Category filter
document.querySelectorAll('.filter-option').forEach(item => {
    item.addEventListener('click', function(e) {
        e.preventDefault();
        const category = item.getAttribute('data-category');

        if (category === 'all') {
            displayEvents(events);
        } else {
            const filteredEvents = events.filter(event => event.category.toLowerCase() === category);
            displayEvents(filteredEvents);
        }
    });
});
// 🧹 Clear Preferences button
const clearPrefsBtn = document.getElementById('clearPrefsBtn');
if (clearPrefsBtn) {
    clearPrefsBtn.addEventListener('click', () => {
        localStorage.removeItem('communityPortalPrefs');
        alert('Your preferences have been cleared.');
        // Optional: refresh the page if needed
        // location.reload();
    });
}



// Geolocation event finder
function setupGeolocationFeature() {
    const findBtn = document.getElementById('findEvents');
    if (!findBtn) return;

    findBtn.addEventListener('click', () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                position => {
                    const { latitude, longitude } = position.coords;
                    document.getElementById('locationInfo').textContent =
                        `Finding events near: ${latitude.toFixed(4)}, ${longitude.toFixed(4)}`;

                    const nearbyEvents = events.filter(event => {
                        const distance = calculateDistance(latitude, longitude, event.location.lat, event.location.lng);
                        return distance <= 10;
                    });

                    displayEvents(nearbyEvents);
                },
                error => {
                    document.getElementById('locationInfo').textContent =
                        'Error getting location: ' + error.message;
                },
                { enableHighAccuracy: true, timeout: 5000 }
            );
        } else {
            document.getElementById('locationInfo').textContent =
                'Geolocation is not supported by this browser.';
        }
    });
    
}
