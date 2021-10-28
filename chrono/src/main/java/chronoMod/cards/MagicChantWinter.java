package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.powers.MagicChantPower;
import chronoMod.powers.MagicChantWinterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static chronoMod.ChronoMod.makeCardPath;

public class MagicChantWinter extends AbstractDynamicCard {
    private static final String CARD_ID = MagicChantWinter.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("skill/" + CARD_ID + ".png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = -2;

    public MagicChantWinter() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.cardsToPreview = new MagicChantSpringTemp();
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = EXTENDED_DESCRIPTION;
        return false;
    }

    @Override
    public void triggerWhenDrawn() {
        this.applyPowers();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower magicChant = p.getPower(MagicChantWinterPower.POWER_ID);
        if (magicChant == null) {
            this.addToBot(new ApplyPowerAction(p, p, new MagicChantWinterPower(p)));
        }
        AbstractCard c = new MagicChantSpring();
        if (this.upgraded) {
            c.upgrade();
        }
        AbstractPower chant = p.getPower(MagicChantPower.POWER_ID);
        if (chant == null) {
            this.addToBot(new ApplyPowerAction(p, p, new MagicChantPower(p, c)));
        }
        this.addToBot(new ExhaustSpecificCardAction(this, p.hand));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.cardsToPreview.upgrade();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MagicChantWinter();
    }
}
