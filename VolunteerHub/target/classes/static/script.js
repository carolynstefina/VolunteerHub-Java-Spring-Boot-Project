// ===============================
// NAVIGATION
// ===============================

function showSection(sectionId) {

    const sections = document.querySelectorAll(".section");

    sections.forEach(section => {
        section.classList.add("hidden");
    });

    document.getElementById(sectionId).classList.remove("hidden");
}


// ===============================
// CREATE VOLUNTEER
// ===============================

async function createVolunteer() {

    const message =
        document.getElementById("volunteerMessage");

    const name =
        document.getElementById("volunteerName").value.trim();

    const email =
        document.getElementById("volunteerEmail").value.trim();

    const phone =
        document.getElementById("volunteerPhone").value.trim();

    if (!name || !email || !phone) {
        message.textContent = "Please fill all volunteer fields.";
        return;
    }

    try {

        const response = await fetch("/api/volunteers", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                name: name,
                email: email,
                phone: phone
            })
        });

        const result = await response.text();

        if (!response.ok) {
            message.textContent = result;
            return;
        }

        const volunteer = JSON.parse(result);

        message.textContent =
            "Volunteer added successfully. ID: " + volunteer.id;

        document.getElementById("volunteerName").value = "";
        document.getElementById("volunteerEmail").value = "";
        document.getElementById("volunteerPhone").value = "";

        loadVolunteers();

    } catch (error) {

        message.textContent =
            "Error: " + error.message;

        console.error(error);
    }
}


// ===============================
// LOAD VOLUNTEERS
// ===============================

async function loadVolunteers() {

    const list =
        document.getElementById("volunteerList");

    try {

        const response =
            await fetch("/api/volunteers");

        if (!response.ok) {
            throw new Error("Failed to load volunteers");
        }

        const volunteers =
            await response.json();

        let html = `
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                </tr>
        `;

        volunteers.forEach(volunteer => {

            html += `
                <tr>
                    <td>${volunteer.id}</td>
                    <td>${volunteer.name}</td>
                    <td>${volunteer.email}</td>
                    <td>${volunteer.phone}</td>
                </tr>
            `;

        });

        html += `</table>`;

        list.innerHTML = html;

    } catch (error) {

        list.innerHTML =
            "<p>Unable to load volunteers.</p>";

        console.error(error);
    }
}


// ===============================
// VIEW TOTAL VOLUNTEER HOURS
// ===============================

async function viewTotalHours() {

    const message =
        document.getElementById("hoursMessage");

    const volunteerId =
        document.getElementById("hoursVolunteerId").value;

    if (!volunteerId) {

        message.textContent =
            "Please enter Volunteer ID.";

        return;
    }

    try {

        const response = await fetch(
            `/api/volunteers/${volunteerId}/hours`
        );

        const result =
            await response.text();

        if (!response.ok) {

            message.textContent =
                "Error: " + result;

            return;
        }

        const hours =
            JSON.parse(result);

        message.textContent =
            "Total contributed hours: " + hours + " hours";

    } catch (error) {

        message.textContent =
            "Error: " + error.message;

        console.error(error);
    }
}


// ===============================
// CREATE EVENT
// ===============================

async function createEvent() {

    const message =
        document.getElementById("eventMessage");

    const name =
        document.getElementById("eventName").value.trim();

    const date =
        document.getElementById("eventDate").value;

    const location =
        document.getElementById("eventLocation").value.trim();

    const capacity =
        document.getElementById("eventCapacity").value;

    if (!name || !date || !location || !capacity) {

        message.textContent =
            "Please fill all event fields.";

        return;
    }

    if (Number(capacity) <= 0) {

        message.textContent =
            "Capacity must be greater than 0.";

        return;
    }

    message.textContent = "Adding event...";

    try {

        const response = await fetch("/api/events", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                name: name,
                date: date,
                location: location,
                capacity: Number(capacity)
            })
        });

        const result =
            await response.text();

        if (!response.ok) {

            message.textContent =
                "Error: " + result;

            return;
        }

        const event =
            JSON.parse(result);

        message.textContent =
            "Event added successfully. ID: " + event.id;

        document.getElementById("eventName").value = "";
        document.getElementById("eventDate").value = "";
        document.getElementById("eventLocation").value = "";
        document.getElementById("eventCapacity").value = "";

        loadEvents();

    } catch (error) {

        message.textContent =
            "Error: " + error.message;

        console.error(error);
    }
}


// ===============================
// LOAD EVENTS
// ===============================

async function loadEvents() {

    const list =
        document.getElementById("eventList");

    try {

        const response =
            await fetch("/api/events");

        if (!response.ok) {
            throw new Error("Failed to load events");
        }

        const events =
            await response.json();

        let html = `
            <table>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Capacity</th>
                </tr>
        `;

        events.forEach(event => {

            html += `
                <tr>
                    <td>${event.id}</td>
                    <td>${event.name}</td>
                    <td>${event.date}</td>
                    <td>${event.location}</td>
                    <td>${event.capacity}</td>
                </tr>
            `;

        });

        html += `</table>`;

        list.innerHTML = html;

    } catch (error) {

        list.innerHTML =
            "<p>Unable to load events.</p>";

        console.error(error);
    }
}


// ===============================
// LOAD UPCOMING EVENTS
// ===============================

async function loadUpcomingEvents() {

    const list =
        document.getElementById("upcomingEventList");

    list.innerHTML = "<p>Loading upcoming events...</p>";

    try {

        const response =
            await fetch("/api/events/upcoming");

        const result =
            await response.text();

        if (!response.ok) {

            list.innerHTML =
                "<p>Error: " + result + "</p>";

            return;
        }

        const events =
            JSON.parse(result);

        if (events.length === 0) {

            list.innerHTML =
                "<p>No upcoming events found.</p>";

            return;
        }

        let html = `
            <table>
                <tr>
                    <th>ID</th>
                    <th>Event Name</th>
                    <th>Date</th>
                    <th>Location</th>
                    <th>Capacity</th>
                    <th>Registered</th>
                    <th>Available Slots</th>
                </tr>
        `;

        events.forEach(event => {

            html += `
                <tr>
                    <td>${event.id}</td>
                    <td>${event.name}</td>
                    <td>${event.date}</td>
                    <td>${event.location}</td>
                    <td>${event.capacity}</td>
                    <td>${event.registered}</td>
                    <td>${event.availableSlots}</td>
                </tr>
            `;

        });

        html += `</table>`;

        list.innerHTML = html;

    } catch (error) {

        list.innerHTML =
            "<p>Unable to load upcoming events.</p>";

        console.error(error);
    }
}


// ===============================
// CREATE SIGNUP
// ===============================

async function createSignup() {

    const message =
        document.getElementById("signupMessage");

    const volunteerId =
        document.getElementById("signupVolunteerId").value;

    const eventId =
        document.getElementById("signupEventId").value;

    if (!volunteerId || !eventId) {

        message.textContent =
            "Please enter Volunteer ID and Event ID.";

        return;
    }

    try {

        const response = await fetch(
            `/api/signups?volunteerId=${volunteerId}&eventId=${eventId}`,
            {
                method: "POST"
            }
        );

        const result =
            await response.text();

        if (!response.ok) {

            message.textContent =
                "Error: " + result;

            return;
        }

        const signup =
            JSON.parse(result);

        message.textContent =
            "Signup successful. Signup ID: " + signup.id;

    } catch (error) {

        message.textContent =
            "Error: " + error.message;

        console.error(error);
    }
}


// ===============================
// MARK ATTENDANCE
// ===============================

async function markAttendance() {

    const message =
        document.getElementById("attendanceMessage");

    const signupId =
        document.getElementById("attendanceSignupId").value;

    const attended =
        document.getElementById("attendanceStatus").value;

    const hours =
        document.getElementById("attendanceHours").value;

    if (!signupId || hours === "") {

        message.textContent =
            "Please enter Signup ID and Hours.";

        return;
    }

    try {

        const response = await fetch(
            `/api/attendance/${signupId}?attended=${attended}&hours=${hours}`,
            {
                method: "PUT"
            }
        );

        const result =
            await response.text();

        if (!response.ok) {

            message.textContent =
                "Error: " + result;

            return;
        }

        message.textContent =
            "Attendance saved successfully.";

    } catch (error) {

        message.textContent =
            "Error: " + error.message;

        console.error(error);
    }
}


// ===============================
// CONNECT BUTTONS
// ===============================

document
    .getElementById("homeButton")
    .addEventListener("click", () => {
        showSection("home");
    });

document
    .getElementById("volunteersButton")
    .addEventListener("click", () => {
        showSection("volunteers");
    });

document
    .getElementById("eventsButton")
    .addEventListener("click", () => {
        showSection("events");
    });

document
    .getElementById("signupsButton")
    .addEventListener("click", () => {
        showSection("signups");
    });

document
    .getElementById("attendanceButton")
    .addEventListener("click", () => {
        showSection("attendance");
    });

document
    .getElementById("addVolunteerButton")
    .addEventListener("click", createVolunteer);

document
    .getElementById("viewVolunteersButton")
    .addEventListener("click", loadVolunteers);

document
    .getElementById("viewHoursButton")
    .addEventListener("click", viewTotalHours);

document
    .getElementById("addEventButton")
    .addEventListener("click", createEvent);

document
    .getElementById("viewEventsButton")
    .addEventListener("click", loadEvents);

document
    .getElementById("viewUpcomingEventsButton")
    .addEventListener("click", loadUpcomingEvents);

document
    .getElementById("registerButton")
    .addEventListener("click", createSignup);

document
    .getElementById("saveAttendanceButton")
    .addEventListener("click", markAttendance);