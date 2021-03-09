describe('200- Get Weather Data ', function () {
    it('Success Response  for the Country UK and City London', function (done) {
        request.get('data/description?county=UK&city=LONDON')
            .set('Content-Type', 'application/json')
            .set('x-api-key', '71793f5b02bb0fe98c7b8720c76cb9f9')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                expect(res.status).to.eql(200);
                expect(res.body.description.length).to.greaterThan(0);
                done();
            });
    });

    it('Success Response with description for the Country Australia and City Melbourne', function (done) {
        request.get('data/description?county=Australia&city=Melbourne')
            .set('Content-Type', 'application/json')
            .set('x-api-key', '71793f5b02bb0fe98c7b8720c76cb9f9')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                expect(res.status).to.eql(200);
                expect(res.body.description.length).to.greaterThan(0);
                done();
            });
    });
});