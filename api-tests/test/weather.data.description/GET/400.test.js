describe('Bad Request Error For Empty Parameters', function () {
    it('Return 400 for invalid country', function (done) {
        request.get('data/description?county=sd&city=Melbourne')
            .set('Content-Type', 'application/json')
            .set('x-api-key', 'd49ebe764fe53c5ca867f87c1ce9c6c7')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                expect(res.status).to.eql(404);
                expect(res.body.message).to.eql('Weather Data Not Found');
                done();
            });
    });

    it('Return 404 for invalid and Country and City', function (done) {
        request.get('data/description?county=India223&city=invalid')
            .set('Content-Type', 'application/json')
            .set('x-api-key', 'd49ebe764fe53c5ca867f87c1ce9c6c7')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                expect(res.status).to.eql(404);
                expect(res.body.message).to.eql('Weather Data Not Found');
                done();
            });
    });
});