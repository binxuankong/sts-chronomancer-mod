package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.BigBangAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;

import static chronoMod.ChronoMod.makeCardPath;

public class BigBang extends AbstractDynamicCard {
    private static final String CARD_ID = BigBang.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("skill/" + CARD_ID + ".png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 0;

    public BigBang() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new BigBangAction());
        this.addToBot(new ApplyPowerAction(p, p, new DoubleDamagePower(p, 1, false), 1));
        // this.addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BigBang();
    }
}
