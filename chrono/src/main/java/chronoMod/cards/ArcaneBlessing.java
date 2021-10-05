package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.characters.Chronomancer;
import chronoMod.powers.ArcaneBlessingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class ArcaneBlessing extends AbstractDynamicCard {
    public static final String ID = ChronoMod.makeID(ArcaneBlessing.class.getSimpleName());
    public static final String IMG = makeCardPath("Power.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 1;
    private static final int EXTRA_DAMAGE = 3;
    private static final int UPGRADE_PLUS_DAMAGE = 2;

    public ArcaneBlessing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = EXTRA_DAMAGE;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new ArcaneBlessingPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ArcaneBlessing();
    }
}
