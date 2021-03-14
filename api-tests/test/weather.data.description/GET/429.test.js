require("it-each")();
require('it-each')({ testPerIteration: true });
let cities = ['Houston', 'Florida', 'Dallas', 'Phoenix', 'Boston', 'Denver']
describe('429- Verify API Key Rate Limit', function () {
    let i = 0;
    it.each(cities, 'Return Hourly Limit Exceeded once the  API Hourly Quota Reaches 5', function (done) {
        request.get('data/description?county=USA&city=' + cities[i])
            .set('Content-Type', 'application/json')
            .set('x-api-key', '8eccabe0fad952681a81b7c4f7f2a2d1')
            .send()
            .end((err, res) => {
                if (err) return done(err);
                if (res.status != 200) {
                    expect(res.status).to.eql(429);
                    expect(res.body.error).to.eql('Limit Reached');
                    expect(res.body.message).to.eql('You have exceeded the hourly limit');
                }
                else {
                    expect(res.status).to.eql(200);
                    expect(res.body.description.length).to.greaterThan(0);
                }
            });
        i++;
    });
});