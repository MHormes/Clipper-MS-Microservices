describe('clipper spec', () => {
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
    describe("Add clipper", () => {
        it("Adds clipper", () => {
            //catch api calls
            cy.intercept("POST", "http://localhost:8071/collection/api/clipper/**").as("addClipper");

            cy.visit("http://localhost:3000/clippers");
            //Go to add page
            cy.get("div:nth-of-type(2) > span").click();
            //insert name
            cy.get("input[type='text']").click();
            cy.get("input[type='text']").type("mascotte #2");
            //insert series
            cy.get("select").select(2);
            //chose number
            cy.get("div:nth-of-type(2) > select").select(1);

            //insert file
            cy.fixture('clipperExample.jpg', 'binary').then(fileContent => {
                //convert to blob
                const blob = Cypress.Blob.binaryStringToBlob(fileContent, 'image/jpeg');

                cy.get("input[type='file']").selectFile({
                    contents: blob,
                    fileName: 'clipperExample.jpg',
                    mimeType: 'image/jpeg'
                });
            });
            //check if image is present
            cy.get('img').should('be.visible');

            //submit
            cy.get("div.card button").click();

            //validate api call success
            cy.wait("@addClipper").its("response.statusCode").should("equal", 200);
            //get redirect to new page
            cy.url().should("include", "/clipper/");
        });
    });
    describe("Update clipper", () => {
        it("Updates clipper", () => {
            //catch api calls
            cy.intercept("PUT", "http://localhost:8071/collection/api/clipper/**").as("updateClipper");

            cy.visit("http://localhost:3000/clippers");
            //Select first clipper
            cy.get("li:nth-of-type(1) div:nth-of-type(1) > button").click();
            //select update option
            cy.get("button:nth-of-type(2)").click();
            //insert name
            cy.get("input[type='text']").click();
            cy.get("input[type='text']").clear();
            cy.get("input[type='text']").type("random name");
            //insert series
            cy.get("div:nth-of-type(1) > select").select(2);
            //chose number
            cy.get("div:nth-of-type(2) > select").select(1);

            //submit
            cy.get("div.card button").click();

            //validate api call success
            cy.wait("@updateClipper").its("response.statusCode").should("equal", 200);
            //get redirect to new page
            cy.url().should("include", "/clipper/");
        });
    });
    describe("Delete clipper", () => {
        it("Deletes clipper", () => {
            //catch api calls
            cy.intercept("DELETE", "http://localhost:8071/collection/api/clipper/**").as("deleteClipper");

            cy.visit("http://localhost:3000/clippers");

            //select first clipper
            cy.get("li:nth-of-type(1) div:nth-of-type(1) > button").click();

            //select delete option
            cy.get("div.card-actions > div:nth-of-type(1) path").click();
            //confirm delete
            cy.get("label.modal > label label:nth-of-type(1)").click();

            //validate api call success
            cy.wait("@deleteClipper").its("response.statusCode").should("equal", 200);
            //get redirect to new page
            cy.url().should("include", "/clippers");
        });
    });


})