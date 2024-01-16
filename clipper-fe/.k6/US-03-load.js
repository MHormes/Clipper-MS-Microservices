import http from 'k6/http';
import { check } from 'k6';


export const options = {
    // Key configurations for avg load test in this section
    stages: [
        { duration: "5s", target: 5 },
        { duration: "10s", target: 100 },
        { duration: "10s", target: 1000 },
        { duration: "10s", target: 2000 },
    ],
};

export default () => {
    const url = 'http://localhost:8071/collection/api/clipper/all';
    const key = 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJNbnZxcTVOMElhWV9JWkpFNmdxLWdUaXFOQXBZeHQ3cDM2ZWt1MUhFSHVJIn0.eyJleHAiOjE3MDU0NDUwOTQsImlhdCI6MTcwNTQwOTA5NSwiYXV0aF90aW1lIjoxNzA1NDA5MDk0LCJqdGkiOiJhNGUzMmFiZi1lMzFjLTQzNWYtYjRjYS0wMTE4Nzg2NTAwYzEiLCJpc3MiOiJodHRwOi8va2V5Y2xvYWstc2VydmljZTo4MTgwL3JlYWxtcy9DbGlwcGVyTVMiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiOWM4MTQ3MjYtMDg3OS00MTE3LTlhYWMtYjNlMzNhMmY2ZDIzIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiY2xpcHBlci1jbGllbnQiLCJub25jZSI6IjU3MjRkNDc3LTc1Y2QtNGYwOS04NDUzLTA1MTMwZjFjNTM3MSIsInNlc3Npb25fc3RhdGUiOiIxOTQyNGU1ZS1kNTFjLTQ3MzktOWViNS1hNGJmYzMzOWRkNTIiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9jbGlwcGVyLW1zLmNvbSIsImh0dHA6Ly9sb2NhbGhvc3Q6ODA3MSIsImh0dHA6Ly9sb2NhbGhvc3Q6ODA3MCIsImh0dHA6Ly9sb2NhbGhvc3Q6MzAwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY2xpcHBlcm1zIiwiUk9MRV9TVVBFUl9BRE1JTiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJzaWQiOiIxOTQyNGU1ZS1kNTFjLTQ3MzktOWViNS1hNGJmYzMzOWRkNTIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJNYWFydGVuIEhvcm1lcyIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIiwiZ2l2ZW5fbmFtZSI6Ik1hYXJ0ZW4iLCJmYW1pbHlfbmFtZSI6Ikhvcm1lcyIsImVtYWlsIjoibWFhcnRlbi5ob3JtZXNAZ21haWwuY29tIn0.g0VNstbzgHV0qilltXHfrXT1KdnGkOe49sQLeZuJ_4qDmkCK8GjRnvocWkIwESEYmm2v4NVEyNhDrslHAOMbYcJfgk8svqAjumrB0YWjymXIbYdf4LMtls-iA9SEo1V2LE0O3E3L_sA_44N5mQA17rpmg9RspryIXfFkumnUr4e_UTkRlF6LOehW-RlYdYDvXB4VMH7-0wyCjOOUE6cvKNP6QujQf1jis8GHOXZWgHjOCB2AkIoVuQdoYPIvrEJCQG7H6GMhZczU3LQSo65knANmS6-vLNyIJlf9ds7mWccc1i0TDFV3aGTSJz50qNq7Gmn4Nz2UPQSA6cto-7MFew'

    const headers = {
        'Content-Type': 'application/json',
        'Authorization' : 'Bearer ' + key
    };

    const result = http.get(url, { headers });

    check(result, {
        '200 return check': (r) => r.status === 200,
    });
};
