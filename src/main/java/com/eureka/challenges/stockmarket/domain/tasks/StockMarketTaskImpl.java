package com.eureka.challenges.stockmarket.domain.tasks;

import com.eureka.challenges.stockmarket.domain.exceptions.InvalidUserException;
import com.eureka.challenges.stockmarket.domain.models.User;
import com.eureka.challenges.stockmarket.domain.ws.ServiceResponse;
import com.eureka.challenges.stockmarket.domain.ws.StockMarketServiceResponse;
import com.eureka.challenges.stockmarket.domain.ws.TimeSeriesDaily;
import com.eureka.challenges.stockmarket.service.StockMarketService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockMarketTaskImpl implements StockMarketTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockMarketTaskImpl.class);
    private StockMarketService stockMarketService;

    StockMarketTaskImpl(StockMarketService stockMarketService) {
        this.stockMarketService = stockMarketService;
    }

    @Override
    public User createUser(String firstName, String lastName, String email) {

        if (firstName.isEmpty() || lastName.isEmpty()) {
            LOGGER.error("firstName or lastName can't be empty");
            throw new InvalidUserException("firstName or lastName can't be empty");
        }

        if (!isValidEmail(email)) {
            LOGGER.error("Invalid email format");
            throw new InvalidUserException("Invalid email format");
        }

        User stockMarketUser = new User(firstName, lastName, email);
        stockMarketUser.setToken(getJWTToken(email));
        return stockMarketUser;
    }

    @Override
    public ServiceResponse getStockData(String symbol) {
        StockMarketServiceResponse stockMarketServiceResponse = stockMarketService.getStockMarket(symbol);

        List<TimeSeriesDaily> keys = stockMarketServiceResponse.getTimeSeriesDaily().values().stream()
                .limit(2)
                .collect(Collectors.toList());

        return buildServiceResponse(keys);
    }

    private ServiceResponse buildServiceResponse(List<TimeSeriesDaily> keys) {
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setOpenPrice(keys.get(0).getOpen());
        serviceResponse.setHigherPrice(keys.get(0).getHigh());
        serviceResponse.setLowerPrice(keys.get(0).getLow());
        serviceResponse.setTwoDayVariation(getTwoDayVariation(keys));
        return serviceResponse;
    }

    private String getTwoDayVariation(List<TimeSeriesDaily> keys) {
        double lastClose = Double.valueOf(keys.get(0).getClose());
        double previousClose = Double.valueOf(keys.get(1).getClose());
        return Double.toString(lastClose - previousClose);
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("eureka")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }

    private boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
