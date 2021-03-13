describe('Bad Request Error For Empty Parameters', function () {
    it('Return Bad Request if country is empty', function (done) {
        request.get('data/description?county=&city=Melbourne')
            .set('Content-Type', 'application/json')
            .set('x-api-key', 'd49ebe764fe53c5ca867f87c1ce9c6c7')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                expect(res.status).to.eql(400);
                expect(res.body.message).to.eql('Empty Query Parameters');
                expect(res.body.errors).contains('Country  is Mandatory');
                done();
            });
    });

    it('Return Bad Request if city is empty', function (done) {
        request.get('data/description?county=India&city=')
            .set('Content-Type', 'application/json')
            .set('x-api-key', 'd49ebe764fe53c5ca867f87c1ce9c6c7')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                expect(res.status).to.eql(400);
                expect(res.body.message).to.eql('Empty Query Parameters');
                expect(res.body.errors).contains('City  is Mandatory');
                done();
            });
    });
});