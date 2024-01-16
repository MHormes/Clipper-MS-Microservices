describe('series spec', () => {
    //todo centralize login function
    //perform login before each test to ensure token is set
    beforeEach(() => {
        cy.intercept("GET", "http://keycloak-service:8180/realms/ClipperMS/protocol/openid-connect/login-status-iframe.html/init?client_id=clipper-client&origin=http%3A%2F%2Flocalhost%3A8080").as("login");

        // Change screen size
        cy.viewport(1920, 1080);
        // Load "http://localhost:3000/home"
        cy.visit("http://localhost:3000");
        // Click on <button> "SIGN IN"
        cy.get(".text-white").click();
        // assign origin for login
        cy.origin("http://keycloak-service:8180", () => {
            // Fill "admin" on <input> #username
            cy.get("#username").type("admin");
            // Fill "admin" on <input> #password
            cy.get("#password").type("admin");
            // Press Enter on input
            cy.get("#password").type("{Enter}");
        });
        //wait to finish login
        cy.url({ timeout: 10000 }).should("include", "/clippers");
    });
    describe("Add series", () => {
        it("Adds series", () => {
            //catch api calls
            cy.intercept("POST", "http://localhost:8071/collection/api/series/**").as("addSeries");

            cy.visit("http://localhost:3000/clippers");
            //go to series
            cy.get("div.navbar-center li:nth-of-type(2) > button").click();
            //Go to add page
            cy.get("div:nth-of-type(2) > span").click();

            cy.get("input[type='text']").type("Testing");

            //insert file
            cy.fixture('seriesExample.jpg', 'binary').then(fileContent => {
                //convert to blob
                const blob = Cypress.Blob.binaryStringToBlob(fileContent, 'image/jpeg');

                cy.get("input[type='file']").selectFile({
                    contents: blob,
                    fileName: 'seriesExample.jpg',
                    mimeType: 'image/jpeg'
                });
            });
            //check if image is present
            cy.get('img').should('be.visible');

            //submit
            cy.get("div.card button").click();

            //validate api call success
            cy.wait("@addSeries").its("response.statusCode").should("equal", 200);
            //get redirect to new page
            cy.url().should("include", "/series/");
        });
    });
    describe("Update series", () => {
        it("Updates series", () => {
            //catch api calls
            cy.intercept("PUT", "http://localhost:8071/collection/api/series/**").as("updateSeries");

            cy.visit("http://localhost:3000/clippers");
            cy.get("div.navbar-center li:nth-of-type(2) > button").click();

            //Select first series
            cy.get("#root > div:nth-of-type(2) li:nth-of-type(2) button").click();

            //click update
            cy.get("div:nth-of-type(2) > button").click();

            //insert name
            cy.get("input[type='text']").clear();
            cy.get("input[type='text']").type("Random text");

            //submit
            cy.get("div.card button").click();

            //validate api call success
            cy.wait("@updateSeries").its("response.statusCode").should("equal", 200);
            //get redirect to new page
            cy.url().should("include", "/series/");
        });
    });
    describe("Delete series", () => {
        it("Deletes series", () => {
            //catch api calls
            cy.intercept("DELETE", "http://localhost:8071/collection/api/series/**").as("deleteSeries");

            cy.visit("http://localhost:3000/clippers");
            //go to series
            cy.get("div.navbar-center li:nth-of-type(2) > button").click();

            //Select first series
            cy.get("#root > div:nth-of-type(2) li:nth-of-type(2) button").click();

            //press delete
            cy.get("div:nth-of-type(2) > div svg").click();

            //confirm
            cy.get("label.modal > label label:nth-of-type(1)").click();

            //validate api call success
            cy.wait("@deleteSeries").its("response.statusCode").should("equal", 200);
            //get redirect to new page
            cy.url().should("include", "/series");
        });
    });
})